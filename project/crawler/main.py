import uuid
import random
import sys
import os
import time
import requests

from azure.data.tables import TableEntity
from azure.storage.blob import BlobServiceClient, BlobClient, ContainerClient

from selenium.webdriver.support import expected_conditions as EC

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.action_chains import ActionChains

from azure.core.credentials import AzureNamedKeyCredential
from azure.data.tables import TableServiceClient

URL = "https://www.coupang.com"

CATEGORY_EN = ['womanclothe', 'manclothe', 'food', 'home_decoration', 'digital', 'office', 'living', 'beauty', 'sports',
               'health', 'kitchen', 'pets', 'hobby', 'car', 'book', 'travel']
CATEGORY_KO = ['여성패션', '남성패션', '식품', '가구/홈인테리어', '가전/디지털', '문구/오피스', '생활용품', '뷰티', '스포츠/레저', '건강식품/헬스', '주방용품',
               '반려동물용품', '완구/취미', '자동차용품', '도서/CD/DVD', '여행']


def get_azure_table_service_from_conn_str(conn_str=None):
    if conn_str is None:
        conn_str = "DefaultEndpointsProtocol=https;AccountName=adgenstorage;AccountKey=u2o5cMTKQIYQewqDFe/H52ovGJhz3KdGpP0ET70Zn1ky1ozreGJoBA6w30C6k8q+QPeJNEpKpmJmfrWWJKIFPg==;EndpointSuffix=core.windows.net"
    return TableServiceClient.from_connection_string(conn_str=conn_str)

def get_azure_blob_service_from_conn_str(conn_str=None):
    if conn_str is None:
        conn_str = "DefaultEndpointsProtocol=https;AccountName=adgenstorage;AccountKey=u2o5cMTKQIYQewqDFe/H52ovGJhz3KdGpP0ET70Zn1ky1ozreGJoBA6w30C6k8q+QPeJNEpKpmJmfrWWJKIFPg==;EndpointSuffix=core.windows.net"
    return BlobServiceClient.from_connection_string(conn_str)

def get_chrome_browser(executable_path=None, options=None):
    driver_dir = "chromedriver"
    platform = sys.platform

    if platform == "win32":
        driver_name = "chromedriver_win32.exe"
    elif platform == "linux":
        driver_name = "chromedriver_linux"
    else:
        raise NotImplementedError(f"Not supported OS required (win32, linux), but got ({os})")

    if options is None:
        options = webdriver.ChromeOptions()
        options.add_argument('--headless')
        options.add_argument("no-sandbox")
        options.add_argument('--disable-blink-features=AutomationControlled')
        options.add_argument("--disable-extensions")
        options.add_argument("disable-gpu")
        options.add_argument("--lang=ko_KR")
        options.add_argument(
            'user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.1072.76 Safari/537.36')
        options.add_experimental_option("excludeSwitches", ["enable-logging"])
        options.add_experimental_option('useAutomationExtension', False)
    if executable_path is None:
        executable_path = os.path.join(os.getcwd(), driver_dir, driver_name)

    return webdriver.Chrome(executable_path=executable_path, options=options)

def get_current_time():
    return time.strftime('%Y-%m-%d %I:%M:%S %p', time.localtime())

def insert_into_blob(path : str, container_client : ContainerClient):
    container_client.create_container

if __name__ == '__main__':

    browser = get_chrome_browser()
    browser.get(url=URL)

    azure_table_service = get_azure_table_service_from_conn_str()
    azure_blob_service = get_azure_blob_service_from_conn_str()
    azure_container_client = azure_blob_service.get_container_client("advertisement")

    bestUnit = WebDriverWait(browser, 5).until(
        EC.presence_of_element_located((By.XPATH, '/html/body/div[3]/section[2]/div[9]')))
    time.sleep(2)
    ActionChains(browser).move_to_element(bestUnit).perform()

    prefix = "categoryBest_"

    azure_table_client = azure_table_service.create_table_if_not_exists("advertisement")
    azure_table_client = azure_table_service.get_table_client("advertisement")
    print(f"[{get_current_time()}] start crawling...")
    for category_en, category_ko in zip(CATEGORY_EN, CATEGORY_KO):
        print(f" - get item with category : [{category_en}]")
        entity = TableEntity()
        bestCategory = WebDriverWait(browser, 10).until(
            EC.presence_of_element_located((By.XPATH, f"//*[@id=\"{prefix + category_en}\"]/dl/dd[3]/ul")))
        prod_list = bestCategory.find_elements(By.TAG_NAME, "li")
        random_num = random.randint(1, len(prod_list))
        item_info = WebDriverWait(browser, 5).until(EC.presence_of_element_located(
            (By.XPATH, f"//*[@id=\"{prefix + category_en}\"]/dl/dd[3]/ul/li[{random_num}]/a")))
        entity['PartitionKey'] = "Advertisement"
        entity['RowKey'] = str(uuid.uuid4()).replace("-", "")
        entity['ItemLink'] = item_info.get_attribute("href")
        entity['Name'] = item_info.find_element(By.CLASS_NAME, "name").get_attribute('textContent')
        entity['Price'] = item_info.find_element(By.CLASS_NAME, "price").find_element(By.TAG_NAME, "strong").get_attribute('textContent')
        entity['CategoryEn'] = category_en
        entity['CategoryKo'] = category_ko
        entity['ImgLink'] = "https://adgenstorage.blob.core.windows.net/advertisement/"+entity['RowKey']+".jpg"

        image_link = item_info.find_element(By.TAG_NAME, "img").get_attribute("src")
        r = requests.get(image_link, allow_redirects=True)
        azure_container_client.upload_blob(name=entity['RowKey']+".jpg", data=r.content)

        print(f"   > save item to azure storage...")
        azure_table_client.create_entity(entity=entity)
        print(f"   > success to save item to azure storage ( Item Id : [{entity['RowKey']}] )")

    print(f"[{get_current_time()}] finish crawling... quit browser")

    browser.quit()

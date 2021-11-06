package com.example.azurestorage.serviceA.service;

import java.util.List;
import java.util.stream.Collectors;

import com.azure.core.credential.AzureSasCredential;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("serviceA.blobService")
public class BlobService {
    
    @Value("${azure.storage.blob-endpoint}")
    String blobEndpoint; // storage account 접근주소입니다.
    @Value("${azure.storage.sas-token}")
    String sasToken;
    @Value("${azure.storage.accesskey}")    
    String accessKey;
    @Value("${azure.storage.container-name}")    
    String containerName;

    Logger log = LoggerFactory.getLogger(BlobService.class);

    public String readBlobByAccessKeyCredential(String containerName, String blobName) {
		
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
			    .endpoint(blobEndpoint)
			    .connectionString(accessKey)
			    .buildClient();
		
		return readBlob(blobServiceClient, containerName, blobName);		
	}

    public String readBlobBySasCredential(String containerName, String blobName) {
		
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
			    .endpoint(blobEndpoint)
                .credential(new AzureSasCredential(sasToken))
			    .buildClient();
		
		return readBlob(blobServiceClient, containerName, blobName);		
	}

    private String readBlob(BlobServiceClient blobServiceClient, String containerName, String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        BlobClient blobClient = containerClient.getBlobClient(blobName);
        
        BinaryData bin = blobClient.downloadContent();
        
        return new String(bin.toBytes());
	}

    public List<String> readBlobListByAccessKeyCredential(String containerName) {
		
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
			    .endpoint(blobEndpoint)
			    .connectionString(accessKey)
			    .buildClient();
		
		return readBlobList(blobServiceClient, containerName);		
	}

    public List<String> readBlobListBySasCredential(String containerName) {
		
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
			    .endpoint(blobEndpoint)
                .credential(new AzureSasCredential(sasToken))
			    .buildClient();
		
		return readBlobList(blobServiceClient, containerName);		
	}

    private List<String> readBlobList(BlobServiceClient blobServiceClient, String containerName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        PagedIterable<BlobItem> blobClient = containerClient.listBlobs();

        return blobClient.stream().map(item-> item.getName()).collect(Collectors.toList());
	}

    public String deleteBlobByAccessKeyCredential(String containerName, String blobName) {
		
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
			    .endpoint(blobEndpoint)
			    .connectionString(accessKey)
			    .buildClient();
		
		return deleteBlob(blobServiceClient, containerName, blobName);		
	}

    public String deleteBlobBySasCredential(String containerName, String blobName) {
		
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
			    .endpoint(blobEndpoint)
                .credential(new AzureSasCredential(sasToken))
			    .buildClient();
		
		return deleteBlob(blobServiceClient, containerName, blobName);		
	}

    private String deleteBlob(BlobServiceClient blobServiceClient, String containerName, String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        containerClient.getBlobClient(blobName).delete();
        return blobName;
    }
}

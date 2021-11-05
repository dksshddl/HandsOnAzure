package com.example.azurestorage.serviceA.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.stream.Collectors;

import com.azure.core.credential.AzureSasCredential;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobListDetails;
import com.azure.storage.blob.models.BlobProperties;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.azure.storage.queue.models.QueueMessageItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        Decoder decoder = Base64.getDecoder();

        return new String(decoder.decode(bin.toBytes()));
	}
}

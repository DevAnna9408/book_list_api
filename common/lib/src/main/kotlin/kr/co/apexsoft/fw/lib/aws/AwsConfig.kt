package kr.co.apexsoft.fw.lib.aws

import com.amazonaws.ClientConfiguration
import com.amazonaws.Protocol
import com.amazonaws.auth.*
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AwsConfig {
    @Value("\${aws.credential.profile}")
    private val awsCredentialProfileName: String? = null

    @Value("\${aws.ses.region}")
    private val mailRegion: String? = null

    @Value("\${aws.s3.signingRegion}")
    private val signingRegion: String? = null

    @Value("\${aws.kms.arn}")
    private val awsKmsArn: String? = null

    @Bean("awsCredentialsProvider")
    fun awsCredentialsProvider(): AWSCredentialsProvider? {
        return AWSCredentialsProviderChain(
            EnvironmentVariableCredentialsProvider(),
            SystemPropertiesCredentialsProvider(),
            ProfileCredentialsProvider(awsCredentialProfileName),
            InstanceProfileCredentialsProvider.getInstance()
        )
    }


    @Bean
    fun sesClient(): AmazonSimpleEmailServiceAsync? {
        return AmazonSimpleEmailServiceAsyncClient.asyncBuilder()
            .withCredentials(awsCredentialsProvider())
            .withRegion(mailRegion)
            .build()
    }

    @Bean
    fun amazonS3Client(): AmazonS3? {
        val clientConfig = ClientConfiguration()
        clientConfig.protocol = Protocol.HTTP
        clientConfig.maxErrorRetry = 10
        clientConfig.connectionTimeout = 9 * 1000
        clientConfig.socketTimeout = 9 * 1000
        clientConfig.maxConnections = 500
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(awsCredentialsProvider())
            .withClientConfiguration(clientConfig)
            .withRegion(signingRegion)
            .build()
    }

    @Bean
    fun amazonKmsProvider(): KmsMasterKeyProvider? {
        return KmsMasterKeyProvider.builder()
            .withCredentials(awsCredentialsProvider())
            .buildStrict(awsKmsArn)

    }
}

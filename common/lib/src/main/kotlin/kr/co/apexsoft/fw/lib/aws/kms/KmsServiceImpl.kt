package kr.co.apexsoft.fw.lib.aws.kms

import com.amazonaws.encryptionsdk.AwsCrypto
import com.amazonaws.encryptionsdk.CryptoResult
import com.amazonaws.encryptionsdk.kms.KmsMasterKey
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider
import com.amazonaws.services.kms.model.AWSKMSException
import kr.co.apexsoft.fw.lib.aws.kms.exception.KMSDecryptException
import kr.co.apexsoft.fw.lib.aws.kms.exception.KMSEncryptException
import kr.co.apexsoft.fw.lib.aws.kms.exception.KMSKeyException
import kr.co.apexsoft.fw.lib.crypto.CryptoService
import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class KmsServiceImpl : CryptoService{
    @Autowired
    private val amazonKmsProvider: KmsMasterKeyProvider? = null


    override fun encrypt(plainText: String?): String? {
        val crypto = AwsCrypto.standard()
        var encryptResult: CryptoResult<ByteArray?, KmsMasterKey?>? = null
        val ciphertext: String
        try {
            encryptResult =
                crypto.encryptData(amazonKmsProvider,
                    plainText?.toByteArray(StandardCharsets.UTF_8)
                )
            val encrypted = Base64.encodeBase64(encryptResult.result)
            ciphertext = String(encrypted)
        } catch (e: AWSKMSException) {
            throw KMSKeyException(e.message)
        } catch (e: Exception) {
            throw KMSEncryptException(e.message)
        }
        return ciphertext
    }


    override fun decrypt(ciphertext: String?): String? {
        if (StringUtils.isBlank(ciphertext)) {
            return ""
        }
        val crypto = AwsCrypto.standard()
        var decryptResult: CryptoResult<ByteArray?, KmsMasterKey?>? = null
        val decryptText: String
        try {
            if (ciphertext != null) {
                decryptResult = crypto.decryptData(
                    amazonKmsProvider, Base64.decodeBase64(
                        ciphertext.toByteArray(
                            StandardCharsets.UTF_8
                        )
                    )
                )
            }
            decryptText = String(decryptResult?.result!!)
        } catch (e: AWSKMSException) {
            throw KMSKeyException(e.message)
        } catch (e: Exception) {
            throw KMSDecryptException(e.message)
        }
        return decryptText
    }
}

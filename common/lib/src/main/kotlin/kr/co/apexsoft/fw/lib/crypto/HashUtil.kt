package kr.co.apexsoft.fw.lib.crypto

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HashUtil {
    private fun getSha256Bytes(originalString: String): ByteArray? {
        var digest: MessageDigest? = null
        digest = try {
            MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
        return digest?.digest(
            originalString.toByteArray(StandardCharsets.UTF_8)
        )
    }

    private fun getSha256Text( originalString: String): String? {
        var digest: MessageDigest? = null
        digest = try {
            MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
        val encodedhash = digest?.digest(
            originalString.toByteArray(StandardCharsets.UTF_8)
        )
        return encodedhash?.let { bytesToHex(it) }
    }

    private fun bytesToHex(hash: ByteArray): String? {
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }

    fun generateHash(value: String): String? {
        return getSha256Text(value)
    }

}

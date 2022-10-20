package kr.co.apexsoft.fw.lib.aws.kms.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class KMSKeyException(error: String?)
    : RuntimeException(MessageUtil.getMessage("KMS_KEY_FAIL") + "/" + error)  {
}

package kr.co.apexsoft.fw.lib.aws.kms.exception

import kr.co.apexsoft.fw.lib.utils.MessageUtil

class KMSDecryptException(error: String?)
    : RuntimeException(MessageUtil.getMessage("KMS_FAIL") + "/" + error)  {
}

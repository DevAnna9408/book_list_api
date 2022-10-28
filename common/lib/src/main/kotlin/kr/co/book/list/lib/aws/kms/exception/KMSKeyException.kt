package kr.co.book.list.lib.aws.kms.exception

import kr.co.book.list.lib.utils.MessageUtil

class KMSKeyException(error: String?)
    : RuntimeException(MessageUtil.getMessage("KMS_KEY_FAIL") + "/" + error)  {
}

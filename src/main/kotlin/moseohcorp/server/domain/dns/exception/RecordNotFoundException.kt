package moseohcorp.server.domain.dns.exception

import moseohcorp.server._common.exception.ApiException
import moseohcorp.server._common.exception.ErrorCode

class RecordNotFoundException : ApiException(ErrorCode.RECORD_NOT_FOUND)

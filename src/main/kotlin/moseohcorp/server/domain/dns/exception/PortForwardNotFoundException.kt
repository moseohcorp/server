package moseohcorp.server.domain.dns.exception

import moseohcorp.server._common.exception.ApiException
import moseohcorp.server._common.exception.ErrorCode

class PortForwardNotFoundException : ApiException(ErrorCode.PORT_FORWARD_NOT_FOUND)

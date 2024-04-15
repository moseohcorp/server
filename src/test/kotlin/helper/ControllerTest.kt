package helper

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.QueryParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.snippet.Snippet
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.time.LocalDateTime

@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
abstract class ControllerTest(
    body: DescribeSpec.() -> Unit = {}
) : DescribeSpec({

    beforeTest {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns LocalDateTime.of(2020, 1, 1, 12, 0, 0)
    }

    afterTest {
        clearAllMocks()
    }

    body()
})

private val objectMapper = ObjectMapper()

infix fun MockHttpServletRequestBuilder.body(value: Any): MockHttpServletRequestBuilder {
    this.contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(value))
    return this
}

infix fun ResultActions.status(value: HttpStatus) {
    this.andExpect(MockMvcResultMatchers.status().`is`(value.value()))
}


fun ResultActions.makeDocument(
    identifier: String,
    vararg snippets: Snippet
): ResultActions {
    return andDo(MockMvcRestDocumentation.document(identifier, *snippets))
}

fun queryParam(vararg fields: RestDocParam): QueryParametersSnippet =
    RequestDocumentation.queryParameters(fields.map { it.descriptor })

fun pathParam(vararg fields: RestDocParam): PathParametersSnippet =
    RequestDocumentation.pathParameters(fields.map { it.descriptor })

fun requestBody(vararg fields: RestDocField): RequestFieldsSnippet =
    PayloadDocumentation.requestFields(fields.map { it.descriptor })

fun responseBody(vararg fields: RestDocField): ResponseFieldsSnippet =
    PayloadDocumentation.responseFields(
        PayloadDocumentation.beneathPath("content").withSubsectionId("content"),
        fields.map { it.descriptor }
    )


/**
 * query, path parameters
 */
infix fun String.means(
    value: String
): RestDocParam {
    return createParam(this, value)
}

private fun createParam(
    value: String,
    means: String,
    optional: Boolean = true
): RestDocParam {
    val descriptor = RequestDocumentation
        .parameterWithName(value)
        .description(means)

    if (optional) descriptor.optional()

    return RestDocParam(descriptor)
}

open class RestDocParam(
    val descriptor: ParameterDescriptor,
) {
    val isIgnored: Boolean = descriptor.isIgnored
    val isOptional: Boolean = descriptor.isOptional

    open infix fun means(value: String): RestDocParam {
        descriptor.description(value)
        return this
    }

    open infix fun attributes(block: RestDocParam.() -> Unit): RestDocParam {
        block()
        return this
    }

    open infix fun isOptional(value: Boolean): RestDocParam {
        if (value) descriptor.optional()
        return this
    }

    open infix fun isIgnored(value: Boolean): RestDocParam {
        if (value) descriptor.ignored()
        return this
    }
}

/**
 * request, response body
 */
infix fun String.fieldType(
    docsFieldType: DocsFieldType
): RestDocField {
    return createField(this, docsFieldType.type)
}

private fun createField(
    value: String,
    type: JsonFieldType,
    optional: Boolean = true
): RestDocField {
    val descriptor = PayloadDocumentation
        .fieldWithPath(value)
        .type(type)
        .description("")

    if (optional) descriptor.optional()

    return RestDocField(descriptor)
}

open class RestDocField(
    val descriptor: FieldDescriptor,
) {
    val isIgnored: Boolean = descriptor.isIgnored
    val isOptional: Boolean = descriptor.isOptional

    open infix fun means(value: String): RestDocField {
        descriptor.description(value)
        return this
    }

    open infix fun attributes(block: RestDocField.() -> Unit): RestDocField {
        block()
        return this
    }

    open infix fun isOptional(value: Boolean): RestDocField {
        if (value) descriptor.optional()
        return this
    }

    open infix fun isIgnored(value: Boolean): RestDocField {
        if (value) descriptor.ignored()
        return this
    }
}

sealed class DocsFieldType(val type: JsonFieldType)

data object ARRAY : DocsFieldType(JsonFieldType.ARRAY)
data object BOOLEAN : DocsFieldType(JsonFieldType.BOOLEAN)
data object OBJECT : DocsFieldType(JsonFieldType.OBJECT)
data object NUMBER : DocsFieldType(JsonFieldType.NUMBER)
data object NULL : DocsFieldType(JsonFieldType.NULL)
data object STRING : DocsFieldType(JsonFieldType.STRING)
data object ANY : DocsFieldType(JsonFieldType.VARIES)
data object DATE : DocsFieldType(JsonFieldType.STRING)
data object DATETIME : DocsFieldType(JsonFieldType.STRING)

import com.mucheng.mucute.bedrock.util.embeddedBedrockServer

suspend fun main() {
    embeddedBedrockServer()
        .listen()
}
/*
Emre Ketme
Distributed Systems CA
*/

package binout.server;

import io.grpc.*;

public class AuthInterceptor implements ServerInterceptor {
    //metadata key for token in headers
    private static final Metadata.Key<String> TOKEN_KEY = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
    //expected token value
    private static final String VALID_TOKEN = "myprojecttoken";
    
    //interceps calls to check token before allowing

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
        ServerCall<ReqT, RespT> call,
        Metadata headers,
        ServerCallHandler<ReqT, RespT> next) {

        String token = headers.get(TOKEN_KEY);
        //close call if token invalid or missing
        if (!VALID_TOKEN.equals(token)) {
            call.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing token"), headers);
            return new ServerCall.Listener<ReqT>() {};
        }
        
        //continue if token is valid

        return next.startCall(call, headers);
    }
}

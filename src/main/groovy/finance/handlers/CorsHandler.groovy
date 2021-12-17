package finance.handlers

import ratpack.handling.Handler
import ratpack.handling.Context
import ratpack.http.HttpMethod
import ratpack.http.Request

import java.net.http.HttpResponse

class CorsHandler implements Handler {

    void handle(Context context) {
        context
                .response
                .headers
                .add('Access-Control-Allow-Origin', '*')
                .add('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, DELETE, PUT')
                .add('Access-Control-Allow-Headers', 'Origin, Content-Type, Accept, Authorization, x-requested-with, content-type')

        if( context.request.method == HttpMethod.OPTIONS  && context.request.path.contains('graphql')) {
            println(context.request.path)
            println('option - sending ok for preflight')
            context.response.send()
            //return
        }

        context.next()
    }
}
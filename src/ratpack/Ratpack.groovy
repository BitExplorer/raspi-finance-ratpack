import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariConfig
import finance.domain.Account
import finance.domain.Category
import finance.domain.Description
import finance.domain.Parameter
import finance.domain.Payment
import finance.domain.Summary
import finance.domain.Transaction
import finance.handlers.CorsHandler
import finance.services.AccountService
import finance.services.CategoryService
import finance.services.DescriptionService
import finance.services.FlywayService
import finance.services.ParameterService
import finance.services.PaymentService
import finance.services.SummaryService
import finance.services.TransactionService
//import gql.ratpack.GraphQLHandler
import ratpack.ssl.SSLContexts
import ratpack.handling.Context
import ratpack.hikari.HikariModule
import ratpack.server.ServerConfigBuilder

import static ratpack.groovy.Groovy.ratpack

ratpack {
    serverConfig { ServerConfigBuilder config ->
       // port(5050)
        port(8443)
        json('db_config.json')
        ssl SSLContexts.sslContext(new File('ssl/hornsup-raspi-finance.jks'), 'monday1')

        //https://github.com/merscwog/ratpack-ssl-test/tree/03b8d325708ae1a3fd20e3c35a5ead178b883703
    }

    bindings {
        Properties hikariConfigProperties = serverConfig.get("/database", Properties)
        moduleConfig(HikariModule, new HikariConfig(hikariConfigProperties))

        bind(CategoryService)
        bind(DescriptionService)
        bind(AccountService)
        bind(PaymentService)
        bind(ParameterService)
        bind(TransactionService)
        bind(SummaryService)
        bindInstance(new FlywayService())
    }

    handlers {
        all(new CorsHandler())

        get ('account/totals') {
            Context context, SummaryService summaryService ->
                context.request.getBody().then {
                    Summary summary = summaryService.summaryAll()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(summary)
                    render(json)
                }
        }

        get('transaction/account/totals/:accountNameOwner') {
            Context context, SummaryService summaryService ->
                context.request.getBody().then {
                    String accountNameOwner = pathTokens["accountNameOwner"]
                    Summary summary = summaryService.summary(accountNameOwner)
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(summary)
                    render(json)
                }
        }

        get('parm/select/:parameterName') {
            Context context, ParameterService parameterService ->
                context.request.getBody().then {
                    String parameterName = pathTokens["parameterName"]
                    Parameter parameter = parameterService.parameter(parameterName)
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(parameter)
                    render(json)
                }
        }

        get ('account/select/active') {
        //get ('accounts') {
            Context context, AccountService accountService ->
                context.request.getBody().then {
                    List<Account> accounts = accountService.accounts()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(accounts)
                    render(json)
                }
        }

        get ('payment/select') {
            Context context, PaymentService paymentService ->
                context.request.getBody().then {
                    List<Payment> payments = paymentService.payments()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(payments)
                    render(json)
                }
        }


        get ('categories') {
            Context context, CategoryService categoryService ->
                context.request.getBody().then {
                    List<Category> categories = categoryService.categories()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(categories)
                    render(json)
                }
        }

        get( 'transaction/account/select/:accountNameOwner') {

            Context context, TransactionService transactionService ->
                context.request.getBody().then {
                    String accountNameOwner = pathTokens["accountNameOwner"]
                    List<Transaction> transactions = transactionService.transactions(accountNameOwner)
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(transactions)
                    render(json)
                }
        }

        get('validation/amount/select/:accountNameOwner/cleared') {
            String accountNameOwner = pathTokens["accountNameOwner"]
            //validation/amount/select/amazon-store_brian/cleared
            render('[]')
        }




        //get ('transactions') {
        get ('transaction/select/all') {
            Context context, TransactionService transactionService ->
                context.request.getBody().then {
                    List<Transaction> transactions = transactionService.transactionsAll()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(transactions)
                    render(json)
                }
        }

        get ('descriptions') {
            Context context, DescriptionService descriptionService ->
                context.request.getBody().then {
                    List<Description> descriptions = descriptionService.descriptions()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(descriptions)
                    render(json)
                }
        }

        delete('transaction/delete/:guid') {
            Context context, TransactionService transactionService ->
                context.request.getBody().then {
                    String guid = pathTokens["guid"]
                    transactionService.deleteTransaction(guid)
                    println('transaction delete called')
                    render('{}')
                }
        }

        post('validation/amount/insert/:accountNameOwner') {
            println('validation amount insert called')
            render('{}')
        }

        //post('graphql', GraphQLHandler)
        post('graphql') {
            render('[]')
        }

        post('transaction/insert') {
            Context context, TransactionService transactionService ->
                context.request.body.then {
                    ObjectMapper objectMapper = new ObjectMapper()
                    Transaction transaction = objectMapper.readValue(it.text, Transaction)
                    Transaction transactionResult = transactionService.insertTransaction(transaction)
                    println transaction
                    render(objectMapper.writeValueAsString(transactionResult))
                }
        }
    }
}

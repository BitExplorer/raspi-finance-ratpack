# raspi-finance-ratpack

## install groovy with sdkman
```
sdk install groovy 2.5.14
sdk default groovy 2.5.14
```

## install ratpack groovy
```
grape -V -Dhttps.protocols=TLSv1.2 install io.ratpack ratpack-groovy 1.3.3
```

## create a new ratpack project
```
lazybones create ratpack 1.9.0 my-rat-app
```
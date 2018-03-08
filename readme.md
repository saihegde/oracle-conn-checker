# Oracle Connection Checker

### Prerquisites

Download the ojdbc6 driver

### How do I run this?

#### From Eclipse

    Add ojdc driver to classpath
    Run as java application supplying the following arguments and in that order
    [dbUrl] [username] [password]

#### From Command Line 
```
java -cp oracle-conn-checker.jar:ojdbc6-12.1.0.1.jar DbConnectionChecker [dbUrl] [username] [password]
```
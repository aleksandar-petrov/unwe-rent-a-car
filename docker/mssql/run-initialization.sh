sleep 15s

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Secret1234 -d master -i create-database.sql
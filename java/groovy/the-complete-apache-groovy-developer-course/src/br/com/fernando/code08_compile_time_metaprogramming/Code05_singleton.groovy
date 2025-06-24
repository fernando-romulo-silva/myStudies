package br.com.fernando.code08_compile_time_metaprogramming

@Singleton
class DbConnection {
	
} 

def dbConnection = DbConnection.instance;

println dbConnection
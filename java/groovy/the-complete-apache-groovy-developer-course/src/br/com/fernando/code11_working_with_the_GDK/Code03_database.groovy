package br.com.fernando.code11_working_with_the_GDK


@Grapes([
	@Grab(group='mysql', module='mysql-connector-java', version='5.1.9'),
	@Grab(group='org.codehaus.groovy', module='groovy-sql', version='3.0.11')
])

import groovy.sql.GroovyRowResult;
import groovy.sql.Sql;

def userName = "twitter"
def password = "password"

def sql = Sql.newInstance("jdbc:mysql://localhost:3306/twitter", userName, password, "com.mysql.jdbc.Driver")
println "Connected!"

// create schema
sql.execute("DROP TABLE IF EXISTS users")
sql.execute("""

	CREATE TABLE users (
		id INT NOT NULL,
		username VARCHAR(45) NOT NULL,
		bio VARCHAR(45) NULL,
		PRIMARY KEY (id)
    );
""")

// create some data
sql.execute("""
	INSERT INTO users (id, username, bio) VALUES (1, 'fernandosilva', 'Programmer.Blogger.YouTuber.Teacher');
""")

def twitterUser = [id:2, username:'foo', bio:'foo'];

sql.execute("""
	INSERT INTO users (id, username, bio)
         VALUES (${twitterUser.id}, ${twitterUser.username}, ${twitterUser.bio})
""")

def rows = sql.rows("select * from users") as List<GroovyRowResult>
println rows

sql.eachRow("select * from users") { row -> 
	println "Tweet: @${row.username}"
}

// calling close manually
sql.close()

println "Completed!"
This is a console application for working with an SFTP server. It allows you to:
• Get a list of "domain - address" pairs from a file
• Get an IP address by domain name
• Get a domain name by IP address
• Add a new "domain - address" pair to a file
• Delete a "domain - address" pair by domain name or IP address

Requirements:
• java SE 8
• Maven
• access to SFTP server
• the JSON file must have a structure similar to the following:
{
"addresses": [
{
"domain": "first.domain",
"ip": "192.168.0.1"
}
]
}
The application will require the following arguments:
1. [username] SFTP server account
2. [server address] host
3. [port] connection port
4. [password] account password
5. [local path] local path to the original JSON file (jsonFile.json)

How to run?
1. Clone the repository
git clone https://github.com/KuznetsovaDS/SFTPTestClient.git
2. Go to the project folder
cd SFTPTestClient
3. Build with using maven
mvn clean package
4. To run, put
java -jar target/SFTPTest-1.0-SNAPSHOT.jar [username] [server address] [port] [password] [local path]

Это консольное приложение для работы с SFTP-сервером.
Оно позволяет осуществлять:
•	Получение списка пар "домен – адрес" из файла
•	Получение IP-адреса по доменному имени
•	Получение доменного имени по IP-адресу
•	Добавление новой пары "домен – адрес" в файл
•	Удаление пары "домен – адрес" по доменному имени или IP-адресу
Требования: 
•	java SE 8 
•	Maven
•	доступ к SFTP-серверу
•	файл JSON должен иметь структуру аналогично следующей:
{
  "addresses": [
    {
      "domain": "first.domain",
      "ip": "192.168.0.1"
    }
  ]
}
Приложение потребует следующие аргументы:
1.	[имя пользователя] учетная запись SFTP сервера
2.	[адрес сервера] хост
3.	[порт] порт подключения
4.	[пароль] пароль к четной записи
5.	[локальный путь] локальный путь к исходному JSON файлу (jsonFile.json)
Как запустить?
1.	Склонируйте репозиторий  
git clone https://github.com/KuznetsovaDS/SFTPTestClient.git
2.	Перейдите в папку проекта
cd SFTPTestClient
3.	Соберите с помощью maven
mvn clean package
4.	Для запуска выполните 
java -jar target/SFTPTest-1.0-SNAPSHOT.jar [имя пользователя] [адрес сервера] [порт] [пароль] [локальный путь]

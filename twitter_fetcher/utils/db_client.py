from cloudant.client import CouchDB


class DBClient:
    """
    This is a couchdb client, we should do operations through it.
    """
    def __init__(self, username, password, url):
        self.client = CouchDB(username, password, url=url, connect=True)

    def get_session(self):
        return self.client.session()

    def get_database(self, db_name):
        return self.client[db_name]

    def get_client(self):
        return self.client

    def mutate_record(self, db_name, record_id, column_names, new_column_values):
        record = self.get_database(db_name)[record_id]
        for i in range(len(column_names)):
            record[column_names[i]] = new_column_values[i]
        record.save()

    def get_query_result(self, database_name, selector, sort=None):
        if sort is None:
            return list(self.get_database(database_name).get_query_result(selector))
        return list(self.get_database(database_name).get_query_result(selector, sort=sort))

    def close_connection(self):
        self.client.disconnect()

    def put_record(self, db_name, record):
        db_name = str(db_name).lower()
        self.create_db(db_name)
        try:
            if record['_id'] not in self.get_database(db_name):
                self.get_database(db_name).create_document(record)
                return True
        except KeyError as e:
            self.get_database(db_name).create_document(record)
            return True
        return False

    def create_db(self, db_name):
        # print(db_name)
        if db_name not in self.client.all_dbs():
            self.client.create_database(db_name)


if __name__ == '__main__':
    client = DBClient('admin', 'password1234', 'http://localhost:5984/')
    # for i in client.get_database("test"):
    #     print(i)
    #
    # print('1149012336147144705' in client.get_database("test"))
    # session = client.get_session()

    # print(client.put_record("hello", {'_ id': '123321', 'name': 'Haoyu'}))
    # client.create_db("hello")
    # print('hello' in client)

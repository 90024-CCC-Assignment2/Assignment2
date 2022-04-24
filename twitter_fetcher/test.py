import json

if __name__ == '__main__':
    with open("test.json", 'r') as f:
        j = json.load(f)
        print(j)

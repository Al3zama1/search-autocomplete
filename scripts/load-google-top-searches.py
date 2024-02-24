
import requests

url = 'http://localhost:8080/api/v1/search'

with open('google-top-searches.txt', 'r') as file:
    for line in file:
        params = {'query': line.strip()}
        response = requests.get(url=url, params=params)
        
        print(f'Response for "{line.strip()}": Status Code - {response.status_code}, Content - {response.text}')

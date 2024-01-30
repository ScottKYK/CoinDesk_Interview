# CoinDesk_Interview
開發軟體 : Java 8  
資料庫: H2 (Spring Data JPA)  
IDEA: IntelliJ 2023

## 實作內容:
1. 呼叫 coindesk 的 API
 - GET:  http://localhost:8080/callCoinDesk  return coindesk API data.
2. 呼叫 coindesk 的 API,並進行資料轉換,組成新 API
 - A. 時間格式: yyyy/MM/dd HH:mm:ss
 - B. 顯示幣別,幣別中文名,匯率
 - GET: http://localhost:8080/newCoinDesk : return new coindesk API data contained
3. 查詢幣別對應表資料
  - GET: http://localhost:8080/find/{code} : get one coin information by Type ,
Request body : code
4. 新增幣別對應表資料
  - POST: http://localhost:8080/add : create a new coin data ,
Request body:name, rate, code, updateTime.
5. 更新幣別對應表資料 , 顯示其內容。
  - PUT: http://localhost:8080/update/{code} : update a coin data
6. 刪除幣別對應表資料
  - DELETE: http://localhost:8080/delete/{code} : delete one coin information by Type ,
Request body : code
7. 以上所有功能均有單元測試。

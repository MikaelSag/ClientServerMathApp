# Instructions to Compile and Execute

## Server

1. Open a terminal window.  
2. If the client and server will be run on different machines, determine the server’s IP address:
   - On **Windows**: run `ipconfig`
   - On **Linux/macOS**: run `ifconfig`
   - Look for the IP address in the form `192.168.x.x` under your active network adapter  
     → The client must use this IP address.

   > *If the client will be run on the same machine as the server, step 2 can be skipped.*

3. Navigate to the directory containing the files `Makefile` and `TCPServer.java`.  
4. In the terminal, run one of the following:
   - `make run-server` &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→ to use the default port `6789`
   - `make run-server PORT=<port>` → to use a different port  
     → *The client must use the same port.*

---

## Client(s)

1. Open a terminal window.  
2. Navigate to the directory containing the files `Makefile` and `TCPClient.java`.  
3. In the terminal, run one of the following:
   - `make run-client`  
     → if the server and client are on the same machine and the server is using the default port `6789`
   - `make run-client IP=<server-ip-address> PORT=<port>`  
     → if the server and client are on different machines or if the server is using a different port
4. Repeat steps 1–3 for any additional clients.


# Parameters Needed During Execution

## Server

- `PORT=<port>`  
  If no value is given, the port number will default to **6789**.

---

## Client

- `IP=<server-ip-address>`  
  If no value is given, the IP address will default to **127.0.0.1**  
  → This will always work if the client and server are being run on the **same machine**.

- `PORT=<port>`  
  If no value is given, the port number will default to **6789**  
  → The port number for the client **must match** the port number used by the server.

---

## Examples

- `make run-server`  
  → Port = **6789**

- `make run-server PORT=6790`  
  → Port = **6790**

- `make run-client`  
  → IP Address = **127.0.0.1**  
  → Port = **6789**

- `make run-client IP=192.168.40.4`  
  → IP Address = **192.168.40.4**  
  → Port = **6789**


# Protocol Design

## Message Format for Sending and Receiving Math Calculations

**Client → Server:**  
`REQ|<client-name>|<expression>`  
*Example:*  
`REQ|Alex|3*4+8`

**Server → Client:**  
`RES|<client-name>|<expression>|<result>`  
*Example:*  
`RES|Alex|3*4+8|20.0`

---

## Message Format for Joining and Terminating Connection

**Join Session (Client → Server):**  
`JOIN|<client-name>`  
*Example:*  
`JOIN|Alex`

**Acknowledge Join (Server → Client):**  
`ACK|Welcome <client-name>`  
*Example:*  
`ACK|Welcome Alex`

**Quit (Client → Server):**  
`QUIT|<client-name>`  
*Example:*  
`QUIT|Alex`

**Disconnect (Server → Client):**  
*No reply.*

---

## Server-Side Logging Format

**Join Log:**  
`JOIN|<client-name>|address=<ip-address:port>`  
*Example:*  
`JOIN|Alex|address=/127.0.0.1:51756`

**Request Result Log:**  
`RES|<client-name>|<expression>|<result>|processingTime=<ms>ms`  
*Example:*  
`RES|Alex|2+3*2|8.0|processingTime=1ms`

**Quit Log:**  
`QUIT|<client-name>`  
*Example:*  
`QUIT|Alex`

- `make run-client IP=192.168.40.4 PORT=6790`  
  → IP Address = **192.168.40.4**  
  → Port = **6790**


# Sample Output

## Server:  
[!serverOutput](https://github.com/MikaelSag/ClientServerMathApp/blob/main/images/serveroutput.png?raw=true)

## Client 1:
![client1Output](https://github.com/MikaelSag/ClientServerMathApp/blob/main/images/client1output.png?raw=true)

## Client 2:
![client2Output](https://github.com/MikaelSag/ClientServerMathApp/blob/main/images/client2output.png?raw=true)

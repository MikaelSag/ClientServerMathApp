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

- `make run-client IP=192.168.40.4 PORT=6790`  
  → IP Address = **192.168.40.4**  
  → Port = **6790**

4. Repeat steps 1–3 for any additional clients.

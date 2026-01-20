package client;

import com.google.gson.Gson;
import dto.RequestDTO;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 20000);

            InputStream keyStream = System.in;
            InputStreamReader keyReader = new InputStreamReader(keyStream);
            BufferedReader keyBuf = new BufferedReader(keyReader);

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);

            InputStream in = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);
            Gson gson = new Gson();



            while(true){
                // 요청

                System.out.println("get 숫자, delete 숫자, post 이름 가격 갯수 : ");
                String input = keyBuf.readLine();

                if (input.equals("exit")){
                    bw.write("exit" + "\n");
                    bw.flush();
                    break;
                }

                String [] parts = input.split(" ");
                String method = parts[0];

                Map<String, Object> qs = null;
                Map<String, Object> body = null;



                if(parts.length == 2 && parts[0].equals("get")){
                    qs = new HashMap<>();
                    qs.put("id", Integer.parseInt(parts[1]));
                }else if (parts.length == 2 && parts[0].equals("delete")){
                    qs = new HashMap<>();
                    qs.put("id", Integer.parseInt(parts[1]));
                }else if(parts.length == 4 && parts[0].equals("post")){
                    body = new HashMap<>();
                    body.put("name", parts[1]);
                    body.put("price", Integer.parseInt(parts[2]));
                    body.put("qty", Integer.parseInt(parts[3]));
                }

                RequestDTO req = new RequestDTO(method, qs, body);
                String json = gson.toJson(req);

                bw.write(json + "\n");
                bw.flush();

                // 응답
                String response = br.readLine();
                System.out.println("server : "+response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

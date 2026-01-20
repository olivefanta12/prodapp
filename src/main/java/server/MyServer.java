package server;

import com.google.gson.Gson;
import dto.RequestDTO;
import dto.ResponseDTO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MyServer {
    private static RequestDTO req;


    public static void main(String[] args) {
        try {
            // 1. 20000 포트로 대기중
            ServerSocket ss = new ServerSocket(20000);
            Socket socket = ss.accept();

            // 2. 새로운 소켓에 버퍼달기 (BR, BW)
            InputStream in = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);
            Gson gson = new Gson();
            ProductService service = new ProductService();
            ProductRepository pre = new ProductRepository();

            while (true) {
                // 1. 클라이언트로 받은 메세지
                String line = br.readLine(); // 엔터키까지 읽기
                if (line == null || line.equals("exit")) {
                    break;
                }
                ResponseDTO<?> resp;

                try {
                    RequestDTO req = gson.fromJson(line, RequestDTO.class);

                    String method = req.getMethod();
                    Map<String, Object> qs = req.getQueryString();
                    Map<String, Object> body = req.getBody();

                    if ("get".equals(method) && qs == null) {
                        resp = new ResponseDTO<>("ok", service.상품목록());

                    } else if ("get".equals(method) && qs != null) {
                        int id = ((Number) qs.get("id")).intValue();
                        resp = new ResponseDTO<>("ok", service.상품상세(id));

                    } else if ("delete".equals(method)) {
                        int id = ((Number) qs.get("id")).intValue();
                        try {
                            service.상품삭제(id);
                            resp = new ResponseDTO<>("ok", null);
                        } catch (RuntimeException e) {
                            resp = new ResponseDTO<>("invaild request", null);
                        }


                    } else if ("post".equals(method)) {
                        String name = ((String) body.get("name"));
                        int price = ((Number) body.get("price")).intValue();
                        int qty = ((Number) body.get("qty")).intValue();

                        service.상품등록(name, price, qty);
                        resp = new ResponseDTO<>("ok", null);
                    } else {
                        resp = new ResponseDTO<>("invaild request", null);
                    }

                    // 2. 파싱 JSON String -> OBJECT

                    // 3. 서비스호출(상품상세)

                    // 4. 응답.

                } catch (Exception e) {
                    resp = new ResponseDTO<>(e.getMessage(), null);
                }
                String json = gson.toJson(resp);
                System.out.println("server : "+line);
                bw.write(json + "\n");
                bw.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //            System.out.println("1: 상품등록, 2: 상품삭제, 3: 상품전체보기, 4: 상품상세");
//            String num = sc.nextLine();
//
//            if (num.equals("1")) {
//                System.out.println("상품명 입력 : ");
//                String a = sc.nextLine();
//
//                System.out.println("상품가격 입력 : ");
//                int b = sc.nextInt();
//
//                System.out.println("상품갯수 입력 : ");
//                int c = sc.nextInt();
//
//                int result = pre.save(a, b, c);
//                System.out.println("결과 : " + result);
//            } else if (num.equals("2")) {
//                System.out.println("삭제할 아이디 번호 : ");
//                int a = sc.nextInt();
//
//                int result = pre.deleteById(a);
//                System.out.println("삭제된 아이디 : " + result);
//            } else if (num.equals("3")) {
//                List<Product> result = pre.findAll();
//
//                for (Product p : result) {
//                    System.out.println(p.toString());
//                }
//            }else if (num.equals("4")){
//                System.out.println("조회할 아이디 입력 : ");
//                int a = sc.nextInt();
//
//                Product result = pre.findById(a);
//                System.out.println(result);
//            }
}

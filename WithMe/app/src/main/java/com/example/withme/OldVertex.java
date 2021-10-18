package com.example.withme;

import com.naver.maps.geometry.LatLng;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class OldVertex {
    public void safeZone(List oldVertex, String name) throws IOException {
        int PORT = 8080;
        String HOST = "118.43.20.83";
        Socket socket=null;
        DataOutputStream dos=null;
        DataInputStream dis=null;

        try {
            socket = new Socket(HOST, PORT);
            // 서버 접속됨
        } catch (IOException e1) {
            // 서버 접속 못함
            e1.printStackTrace();
        }

        try {
            dos = new DataOutputStream(socket.getOutputStream());   // output으로 보내느 스트림
            dis = new DataInputStream(socket.getInputStream());     // input으로 받는 스트림
            // 안드로이드에서 서버로 연결 요청

        } catch (IOException e) {
            e.printStackTrace();
            // 버퍼 생성 잘못됨
        }

        try {
            String safe_zone = oldVertex + "|" + name;
            String sendData = safe_zone.replaceAll(",","|");
            byte[] data = sendData.getBytes();
            ByteBuffer b = ByteBuffer.allocate(4);
            b.order(ByteOrder.LITTLE_ENDIAN);
            b.putInt(data.length);
            dos.write(b.array(), 0, 4);
            dos.write(data);
            //dos.flush();

            data = new byte[4];
            dis.read(data, 0, 4);
            ByteBuffer b1 = ByteBuffer.wrap(data);
            b1.order(ByteOrder.LITTLE_ENDIAN);
            int length = b1.getInt();
            data = new byte[length];
            dis.read(data, 0, length);
            String msg = new String(data, "UTF-8");
            if (msg.equals("1")) {
                System.out.println("the safe_zone can be accessed");
            }
            else {
                System.out.println("you have to try safe_zone part again. please draw larger than before size");
            }

            System.out.println("the server outcome is" + msg);

            ByteBuffer b2 = ByteBuffer.allocate(4);
            b2.order(ByteOrder.LITTLE_ENDIAN);
            b2.putInt("exit".getBytes().length);
            dos.write(b2.array(), 0, 4);
            dos.write("exit".getBytes());
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

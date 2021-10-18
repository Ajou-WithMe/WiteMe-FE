package com.example.withme;

import android.util.Log;

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
        String message = null;
        String HOST = "118.43.20.83";
        Socket socket = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;

        Log.e("1", "Old Vertex 객체 생성 완료");

        try {
            socket = new Socket(HOST, PORT);
            Log.e("2", "서버 접속됨!");

            // 서버 접속됨
        } catch (IOException e1) {
            // 서버 접속 못함
            Log.e("2", "서버 접속 못함!");
            e1.printStackTrace();
        }

        try {
            dos = new DataOutputStream(socket.getOutputStream());   // output으로 보내느 스트림
            dis = new DataInputStream(socket.getInputStream());     // input으로 받는 스트림
            Log.e("3", "안드로이드에서 서버로 연결 요청");
            // 안드로이드에서 서버로 연결 요청

        } catch (IOException e) {
            e.printStackTrace();
            // 버퍼 생성 잘못됨
        }

        try {
            Log.e("4", "데이터 성공적으로 전송 완료");
            String safe_zone = oldVertex + "|" + name;
            String sendData = safe_zone.replaceAll(",", "|");
            byte[] data = sendData.getBytes();
            ByteBuffer b = ByteBuffer.allocate(4);
            b.order(ByteOrder.LITTLE_ENDIAN);
            b.putInt(data.length);
            dos.write(b.array(), 0, 4);
            dos.write(data);

            data = new byte[4];
            dis.read(data, 0, 4);
            ByteBuffer b1 = ByteBuffer.wrap(data);
            b1.order(ByteOrder.LITTLE_ENDIAN);
            int length = b1.getInt();
            data = new byte[length];
            dis.read(data, 0, length);
            message = new String(data, "UTF-8");
            if (message.equals("1")) {
                Log.e("old vertex", "the safe_zone can be accessed");
            } else {
                Log.e("old vertex", "you have to try safe_zone part again. please draw larger than before size");
            }
            Log.e("old vertex", "the server outcome is" + message);

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

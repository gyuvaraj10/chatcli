package net.uni.chat.cli.tests.clients;

import net.uni.chat.cli.poc.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.PipedOutputStream;
import java.lang.reflect.Field;
import java.net.Socket;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "net.uni.chat.cli.poc.Client")
public class TestClient {

    private String host = "localhost";
    private int port = 8888;

    @Test
    public void testInitializeClient() throws Exception {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("userName");
        field.setAccessible(true);
        field.set(client, "Test");
        Socket socket = PowerMockito.mock(Socket.class);
        PowerMockito.whenNew(Socket.class).withAnyArguments().thenReturn(socket);
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PowerMockito.when(socket.getOutputStream()).thenReturn(pipedOutputStream);
        client.runClient(host,port);
    }
}

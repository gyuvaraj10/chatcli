package net.uni.chat.cli.tests.clients;

import net.uni.chat.cli.poc.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.Console;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Field;
import java.net.Socket;

import static org.mockito.ArgumentMatchers.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"net.uni.chat.cli.poc.Client", "java.io.Console"})
public class TestClient {

    private String host = "localhost";
    private int port = 8888;

    @Test
    public void testInitializeClient() throws NoSuchFieldException, IOException, IllegalAccessException {
        Client client = new Client();
        Field field = client.getClass().getDeclaredField("userName");
        field.setAccessible(true);
        field.set(client, "Test");
        Console console = PowerMockito.mock(Console.class);
        Socket socket = PowerMockito.mock(Socket.class);
        try {
            PowerMockito.whenNew(Socket.class).withAnyArguments().thenReturn(socket);
            PowerMockito.when(console.readLine(anyString())).thenReturn("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PowerMockito.when(socket.getOutputStream()).thenReturn(pipedOutputStream);
        PipedInputStream pipedInputStream = new PipedInputStream();
        PowerMockito.when(socket.getInputStream()).thenReturn(pipedInputStream);
        client.runClient(host,port, console);
    }
}

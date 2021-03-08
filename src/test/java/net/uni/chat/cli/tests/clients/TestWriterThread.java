package net.uni.chat.cli.tests.clients;

import net.uni.chat.cli.poc.WriterThread;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"net.uni.chat.cli.poc.WriterThread"})
public class TestWriterThread {

    @Test
    public void testWriteMessage() throws Exception {
        Console console = PowerMockito.mock(Console.class);
        Socket socket = PowerMockito.mock(Socket.class);
        PowerMockito.whenNew(Socket.class).withAnyArguments().thenReturn(socket);
        PowerMockito.when(console.readLine(anyString())).thenReturn("test");
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PowerMockito.when(socket.getOutputStream()).thenReturn(pipedOutputStream);
        WriterThread writerThread = new WriterThread(socket,  "Test", console);
        Field field = writerThread.getClass().getDeclaredField("writer");
        field.setAccessible(true);
        PrintWriter writer = (PrintWriter)field.get(writerThread);
        writer  = PowerMockito.spy(writer);
        field.set(writerThread, writer);
        String[] values = new String[] {"Server: New User joined Test,127.0.0.1","Test:test"};
        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                String value = invocationOnMock.getArgument(0);
                Assert.assertTrue(Arrays.stream(values).anyMatch(x->x.equalsIgnoreCase(value)));
                return  null;
            }
        }).when(writer, "println", anyString());
        Thread thread = new Thread(() -> {
            try {
                writerThread.writeMessage();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(10000);
        writerThread.setDoNotTerminate(false);
    }
}

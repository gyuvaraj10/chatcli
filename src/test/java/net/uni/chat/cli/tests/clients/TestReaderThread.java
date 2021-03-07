package net.uni.chat.cli.tests.clients;

import net.uni.chat.cli.poc.ReaderThread;
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
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"net.uni.chat.cli.poc.ReaderThread"})
public class TestReaderThread {

    @Test
    public void testReaderThread() throws Exception {
        PrintStream printStream = PowerMockito.spy(System.out);
        Socket socket = PowerMockito.mock(Socket.class);
        try {
            PowerMockito.whenNew(Socket.class).withAnyArguments().thenReturn(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PipedInputStream pipedInputStream = new PipedInputStream();
        PowerMockito.when(socket.getInputStream()).thenReturn(pipedInputStream);
        ReaderThread readerThread = new ReaderThread(socket, printStream);
        Field field = readerThread.getClass().getDeclaredField("reader");
        field.setAccessible(true);
        BufferedReader reader = (BufferedReader)field.get(readerThread);
        reader = PowerMockito.spy(reader);
        field.set(readerThread, reader);
        PowerMockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "testReaderThread";
            }
        }).when(reader,"readLine");

        Field field1 = readerThread.getClass().getDeclaredField("printStream");
        field1.setAccessible(true);
        field1.set(readerThread, printStream);
        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                String value = invocationOnMock.getArgument(0);
                Assert.assertEquals(value, "\ntestReaderThread");
                return null;
            }
        }).when(printStream, "println", anyString());
        Thread thread = new Thread(readerThread::readMessage);
        thread.start();
        Thread.sleep(10000);
        readerThread.setDoNotTerminate(false);
    }

}

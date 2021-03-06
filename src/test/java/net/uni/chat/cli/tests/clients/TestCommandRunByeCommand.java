package net.uni.chat.cli.tests.clients;

import net.uni.chat.cli.poc.ClientReaderWriter;
import net.uni.chat.cli.poc.CommandRunner;
import net.uni.chat.cli.poc.Server;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"net.uni.chat.cli.poc.CommandRunner"})
public class TestCommandRunByeCommand {

    @Test
    public void testRunByeCommand() throws Exception {
        Server server = new Server();
        server = PowerMockito.spy(server);
        Socket socket = PowerMockito.mock(Socket.class);
        PipedInputStream pipedInputStream = new PipedInputStream();
        PowerMockito.when(socket.getInputStream()).thenReturn(pipedInputStream);
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PowerMockito.when(socket.getOutputStream()).thenReturn(pipedOutputStream);
        ClientReaderWriter clientReaderWriter = new ClientReaderWriter(socket, server);
        clientReaderWriter.setRole("Coordinator");
        clientReaderWriter.setUserName("SpiderMan");
        clientReaderWriter = PowerMockito.spy(clientReaderWriter);
        ClientReaderWriter clientReaderWriter2 = new ClientReaderWriter(socket, server);
        clientReaderWriter2.setRole("Member");
        clientReaderWriter2.setUserName("GhostRider");
        clientReaderWriter2 = PowerMockito.spy(clientReaderWriter2);
        Field field = Server.class.getDeclaredField("set");
        field.setAccessible(true);
        Set<ClientReaderWriter> set = (Set<ClientReaderWriter>)field.get(null);
        set.add(clientReaderWriter);
        set.add(clientReaderWriter2);
        field.set(null, set);
        CommandRunner commandRunner = new CommandRunner(server, clientReaderWriter);
        CommandRunner commandRunner2 = new CommandRunner(server, clientReaderWriter2);
        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Assert.assertTrue(true);
                return "invocationOnMock.callRealMethod()";
            }
        }).when(clientReaderWriter, "sendMessage", anyString());

        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Assert.assertTrue(true);
                return "invocationOnMock.callRealMethod()";
            }
        }).when(clientReaderWriter2, "sendMessage", anyString());
        commandRunner.runCommand("Test:bye");
        commandRunner2.runCommand("Test:bye");
    }
}

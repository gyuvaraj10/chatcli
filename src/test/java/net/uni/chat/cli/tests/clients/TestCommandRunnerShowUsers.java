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
public class TestCommandRunnerShowUsers {

    @Test
    public void testRunCommandShowUsersForCoordinator() throws Exception {
        Server server = new Server();
        server = PowerMockito.spy(server);
        Socket socket = PowerMockito.mock(Socket.class);
        PipedInputStream pipedInputStream = new PipedInputStream();
        PowerMockito.when(socket.getInputStream()).thenReturn(pipedInputStream);
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PowerMockito.when(socket.getOutputStream()).thenReturn(pipedOutputStream);
        ClientReaderWriter crwCoordinator = new ClientReaderWriter(socket, server);
        crwCoordinator.setRole("Coordinator");
        crwCoordinator.setUserName("SpiderMan");
        crwCoordinator = PowerMockito.spy(crwCoordinator);
        ClientReaderWriter crwMember = new ClientReaderWriter(socket, server);
        crwMember.setRole("Member");
        crwMember.setUserName("GhostRider");
        crwMember = PowerMockito.spy(crwMember);
        Field field = Server.class.getDeclaredField("set");
        field.setAccessible(true);
        Set<ClientReaderWriter> set = (Set<ClientReaderWriter>)field.get(null);
        set.add(crwCoordinator);
        set.add(crwMember);
        field.set(null, set);
        CommandRunner commandRunnerCoordinator = new CommandRunner(server, crwCoordinator);
        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Assert.assertTrue(true);
                return "invocationOnMock.callRealMethod()";
            }
        }).when(crwCoordinator, "sendMessage", anyString());

        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Assert.fail("Expected Not to call this method");
                return "invocationOnMock.callRealMethod()";
            }
        }).when(crwMember, "sendMessage", anyString());
        commandRunnerCoordinator.runCommand("Test:show users");
    }


}

package top.fixyou;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lsk
 * @description
 * @date 2023/4/12 11:29
 */

public class MainTest {

    public static void main(String[] args) throws IOException, JSchException {
//        com.jcraft.jsch.Session remoteSession = JschUtil.getSession("fixyou.top", 22, "root", "TXljh257248");
//        List<Double> doubles = doCmd(remoteSession,
//                "top -b -n1 | fgrep \"Cpu(s)\" | awk '{printf \"%d\\n\",100-$8}'"
//        );
//        System.out.println(doubles);
        int state = 400;
        System.out.println(!(state == 400 || state == 200 || state == 500));
    }

    public static List<Double> doCmd(Session session, String... cmds) throws JSchException, IOException {
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        String allCmd = String.join(" && ", cmds);
        exec.setCommand(allCmd);
        exec.connect();
        List<String> result;
        try (InputStream in = exec.getInputStream();
             InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            result = reader.lines().collect(Collectors.toList());
        }
        exec.disconnect();
        session.disconnect();
        System.out.println(allCmd);
        return result.stream().map(str -> Double.valueOf(str.replace("%", ""))).collect(Collectors.toList());
    }

//  top -n1 | fgrep "Cpu(s)" | awk '{printf "%d\n",100-$8}' && free -m | fgrep "Mem" | awk '{printf "%d\n", ($3)/$2*100}'  &&  df -h | fgrep "/dev/vda1" | awk '{print $5}'


}

package com.little.pang.boring.code.netty.messagepack;

import com.google.common.collect.Lists;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.List;

/**
 * Created by jaky on 4/5/16.
 */
public class MessagePackTest {

    public static void main(String[] args) throws IOException {
        List<String> names = Lists.newArrayList("jaky", "wang", "xiao", "pang");

        MessagePack msgPack = new MessagePack();

        // Serialize
        byte[] raw = msgPack.write(names);

        List<String> decodeNames = msgPack.read(raw, Templates.tList(Templates.TString));

        System.out.println(decodeNames);
    }

}

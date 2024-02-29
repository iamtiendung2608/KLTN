package com.block_chain.KLTN.helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;

import java.io.File;
import java.util.*;

@Getter
@Setter
public class Mail extends SimpleMailMessage {
    private Map<String, Object> props;
    private String template;
    private List<File> files = new ArrayList<>();

    public void addProp(String key, Object value) {
        if (Objects.isNull(props)) {
            props = new HashMap<>();
        }
        props.put(key, value);
    }
}

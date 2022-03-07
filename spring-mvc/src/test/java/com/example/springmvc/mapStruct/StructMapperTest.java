package com.example.springmvc.mapStruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

/**
 * @author Hodur
 * @date 2022/02/10
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StructMapperTest {

    @Autowired
    private StructMapper structMapper;

    @Test
    public void toTarget() throws JsonProcessingException {

        Source.InnerSource innerSource = new Source.InnerSource();
        innerSource.setName("innerSource");
        innerSource.setDeleted(1);

        Source source = new Source();
        source.setSourceName("source");
        source.setId(1);
        source.setInnerSource(innerSource);
        source.setSources(Collections.singletonList(innerSource));

        Target target = StructMapper.INSTANCE.toTarget(source);
        System.out.println(new JsonMapper().writeValueAsString(target));
    }

    @Test
    public void toSource() throws JsonProcessingException {

        Target.InnerTarget innerTarget = new Target.InnerTarget();
        innerTarget.setName("innerTarget");
        innerTarget.setIsDeleted(true);

        Target target = new Target();
        target.setTargetName("target");
        target.setId(1);
        target.setInnerTarget(innerTarget);
        target.setTargets(Collections.singletonList(innerTarget));

        Source source = structMapper.toSource(target);
        System.out.println(new JsonMapper().writeValueAsString(source));
    }
}
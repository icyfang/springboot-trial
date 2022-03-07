package com.example.springmvc.mapStruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Hodur
 * @date 2022/02/10
 */
@Mapper(componentModel = "spring")
public interface StructMapper {

    @Mapping(source = "sourceName", target = "targetName")
    @Mapping(source = "innerSource", target = "innerTarget")
    @Mapping(source = "sources", target = "targets")
    Target toTarget(Source source);

    @Mapping(source = "targetName", target = "sourceName")
    @Mapping(source = "innerTarget", target = "innerSource")
    @Mapping(source = "targets", target = "sources")
    Source toSource(Target source);

    default Target.InnerTarget innerTarget2InnerSource(Source.InnerSource innerSource) {
        if (innerSource == null) {
            return null;
        }
        Target.InnerTarget innerTarget = new Target.InnerTarget();
        innerTarget.setIsDeleted(innerSource.getDeleted() == 1);
        innerTarget.setName(innerSource.getName());
        return innerTarget;
    }

    default Source.InnerSource innerSource2InnerTarget(Target.InnerTarget innerTarget) {
        if (innerTarget == null) {
            return null;
        }
        Source.InnerSource innerSource = new Source.InnerSource();
        innerSource.setDeleted(innerTarget.getIsDeleted() ? 1 : 0);
        innerSource.setName(innerTarget.getName());
        return innerSource;
    }

    StructMapper INSTANCE = Mappers.getMapper(StructMapper.class);

}
package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.MemberRequestDTO;
import com.library.library_management_system.dto.MemberResponseDTO;
import com.library.library_management_system.entity.Member;
import org.springframework.stereotype.Component;
@Component
public class MemberMapper {
    public Member  toEntity(MemberRequestDTO dto) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPhoneNumber(dto.getPhoneNumber());
        return member;
    }
    public MemberResponseDTO toResponseDTO(Member member) {
        MemberResponseDTO dto = new MemberResponseDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPhoneNumber(member.getPhoneNumber());
        return dto;
    }
}

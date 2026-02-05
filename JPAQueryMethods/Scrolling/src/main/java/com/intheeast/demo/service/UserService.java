package com.intheeast.demo.service;


import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.WindowIterator;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Window;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Offset 기반 스크롤링 처리
    public List<UserDTO> getUsersWithOffset(int page, int size) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));
        return userPage.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<UserDTO> getBooksUsingOffset(Integer age) {
        OffsetScrollPosition offset = ScrollPosition.offset();
        Window<User> userReviews = userRepository.findFirst5ByAge(age, offset);
        
        if (userReviews.isEmpty()) {
            // 로그 추가
            System.out.println("No users found for age: " + age);
            return Collections.emptyList(); // 빈 리스트 반환
        }

        List<User> userReviewsResult = new ArrayList<>();       
        

        do {
            userReviews.forEach(userReviewsResult::add);
            
            if (!userReviews.isEmpty()) {
                offset = (OffsetScrollPosition) userReviews.positionAt(userReviews.size() - 1);
                userReviews = userRepository.findFirst5ByAge(age, offset);
            } else {
                break; // userReviews가 비어있으면 루프를 종료
            }
            
        } while (userReviews.hasNext());

        // Stream으로 변환하여 UserDTO로 변환
        List<UserDTO> result = userReviewsResult.stream()
            .map(UserDTO::fromEntity)
            .collect(Collectors.toList());

        return result;
    }


    // Keyset-Filtering 기반 스크롤링 처리 (WindowIterator 사용)
    public List<UserDTO> getUsersWithWindowIterator(int ageThreshold) {

        WindowIterator<User> iterator = WindowIterator.of(
        		(ScrollPosition position) -> 
        		userRepository.findByAgeGreaterThanOrderByAgeAsc(
        				ageThreshold, (KeysetScrollPosition) position))
        		.startingAt(ScrollPosition.keyset());

        List<UserDTO> result = new ArrayList<>();
        iterator.forEachRemaining(user -> result.add(UserDTO.fromEntity(user)));

        return result;
    }
}
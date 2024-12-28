package com.unibooking.web;

import com.unibooking.service.RoomService;
import com.unibooking.service.dto.RoomDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    private ResponseEntity<Void> createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        roomService.createRoom(roomDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    private ResponseEntity<Page<RoomDTO>> getRoomsPage(Pageable pageable) {
        return ResponseEntity.ok(roomService.findAllRooms(pageable));
    }
}

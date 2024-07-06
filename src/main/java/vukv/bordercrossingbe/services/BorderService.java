package vukv.bordercrossingbe.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vukv.bordercrossingbe.repositories.BorderRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BorderService {

    private final BorderRepository borderRepository;

}

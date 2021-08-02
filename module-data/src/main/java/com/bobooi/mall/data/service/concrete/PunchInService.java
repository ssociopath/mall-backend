package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.data.repository.concrete.PunchInRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.bobooi.mall.data.entity.PunchIn;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author bobo
 * @date 2021/4/20
 */

@Service
@Slf4j
public class PunchInService extends BaseDataService<PunchIn, Integer> {
    @Resource
    private PunchInRepository punchInRepository;

    public static boolean checkHasPunchedIn(int bitmap, int dayOfMonth) {
        return (bitmap &= (1 << (1 - dayOfMonth))) != 0;
    }

    public static int punchIn(int bitmap, int dayOfMonth) {
        return bitmap |= (1 << (1 - dayOfMonth));
    }

    public PunchIn getMonthlyPunchIn(Integer userId, LocalDate today){
        return punchInRepository.findPunchInByCustomerIdAndYearAndMonth(userId, today.getYear(), today.getMonthValue())
                .orElseGet(()-> new PunchIn(userId, today.getYear(), today.getMonthValue(), 0));
    }

    public boolean dailyPunchIn(Integer userId, LocalDate today) {
        PunchIn punchIn = getMonthlyPunchIn(userId, today);
        if (checkHasPunchedIn(punchIn.getDailyBitmap(), today.getDayOfMonth())) {
            return false;
        }
        punchIn.setDailyBitmap(punchIn(punchIn.getDailyBitmap(), today.getDayOfMonth()));
        punchInRepository.save(punchIn);
        return true;
    }
}

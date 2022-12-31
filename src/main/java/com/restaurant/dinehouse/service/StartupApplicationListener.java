package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.Category;
import com.restaurant.dinehouse.model.Item;
import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.repository.CategoryRepository;
import com.restaurant.dinehouse.repository.ItemRepository;
import com.restaurant.dinehouse.repository.UserRepository;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
        value="system.startup-event.enable",
        havingValue = "true",
        matchIfMissing = true)
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SystemConfig systemConfig;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        categoryRepository.deleteAll();
        itemRepository.deleteAll();

        Map<Integer, Category> categoryMap = new HashMap<>();
        try {
            List<String> records = systemConfig.readSourceAsStream("/category.csv");
            records.forEach(record -> {
                log.info("record {}",record);
                String[] tuples = record.split(",");
                Category category = new Category();
                category.setStatus(true);
                category.setId(Integer.parseInt(tuples[0]));
                category.setName(tuples[1]);
                categoryRepository.save(category);
            });
        } catch (IOException e) {
            log.error("failed to read content /category.csv from class-path resource {}", e.getMessage());
        }

        Iterable<Category> categoryIterable = categoryRepository.findAll();
        categoryIterable.iterator().forEachRemaining(category -> categoryMap.put(category.getId(),category));

        try {
            List<String> records = systemConfig.readSourceAsStream("/items.csv");
            records.forEach(record -> {
                log.info("record {}",record);
                String[] tuple = record.split(",");
                Item item = new Item();
                item.setCategoryId(Integer.parseInt(tuple[0]));
                item.setCategoryName(categoryMap.get(item.getCategoryId()).getName());
                item.setName(tuple[2]);
                item.setStatus(SystemConstants.ITEM_ACTIVE_STATUS);
                item.setPrice(Double.parseDouble(tuple[3]));
                item.setVeg(StringUtils.equals("1", tuple[1]) ? true : false);
                item.setUserId(SystemConstants.SYS_ADMIN_USER);
                itemRepository.save(item);
            });
        } catch (IOException e) {
            log.error("failed to read content /items.csv from class-path resource {}", e.getMessage());
        }

        User user = userRepository.findByUserId(SystemConstants.SYS_ADMIN_USER);
        if (Objects.isNull(user)) {
            User adminUser = systemConfig.getSystemUserById(SystemConstants.SYS_ADMIN_USER);
            userRepository.save(adminUser);
        }
        user = userRepository.findByUserId(SystemConstants.SYS_MISC_USER);
        if (Objects.isNull(user)) {
            User miscUser = systemConfig.getSystemUserById(SystemConstants.SYS_MISC_USER);
            userRepository.save(miscUser);
        }
    }
}

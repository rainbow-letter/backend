package com.rainbowletter.server.faq.adapter.out.infrastructure;

import static com.rainbowletter.server.faq.adapter.out.infrastructure.QFaqJpaEntity.faqJpaEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import com.rainbowletter.server.faq.application.port.out.DeleteFaqPort;
import com.rainbowletter.server.faq.application.port.out.LoadFaqPort;
import com.rainbowletter.server.faq.application.port.out.SaveFaqPort;
import com.rainbowletter.server.faq.application.port.out.UpdateFaqStatePort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class FaqPersistenceAdapter implements SaveFaqPort, LoadFaqPort, UpdateFaqStatePort, DeleteFaqPort {

    private final FaqMapper faqMapper;
    private final FaqJpaRepository faqJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Faq saveFaq(final Faq faq) {
        final FaqJpaEntity faqJpaEntity = faqJpaRepository.save(faqMapper.mapToJpaEntity(faq));
        return faqMapper.mapToDomain(faqJpaEntity);
    }

    @Override
    public Faq loadFaq(final FaqId faqId) {
        return faqMapper.mapToDomain(
            faqJpaRepository.findById(faqId.value())
                .orElseThrow(() -> new RainbowLetterException("FAQ 리소스를 찾을 수 없습니다.", faqId))
        );
    }

    @Override
    public List<Faq> loadFaqs() {
        return queryFactory.selectFrom(faqJpaEntity)
            .orderBy(faqJpaEntity.sequence.asc())
            .fetch()
            .stream()
            .map(faqMapper::mapToDomain)
            .toList();
    }

    @Override
    public List<Faq> loadVisibilityFaqs() {
        return queryFactory.selectFrom(faqJpaEntity)
            .where(faqJpaEntity.visibility.isTrue())
            .orderBy(faqJpaEntity.sequence.asc())
            .fetch()
            .stream()
            .map(faqMapper::mapToDomain)
            .toList();
    }

    @Override
    public void updateFaq(final Faq faq) {
        saveFaq(faq);
    }

    @Override
    public void deleteFaq(final FaqId faqId) {
        faqJpaRepository.deleteById(faqId.value());
    }

}

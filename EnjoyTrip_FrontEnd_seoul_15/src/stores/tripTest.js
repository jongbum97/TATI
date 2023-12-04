import {ref, computed, watch} from "vue";
import { defineStore } from "pinia";
import { modify } from "@/api/user";

export const useTripTestStore = defineStore("tripTest", () => {

    const resultType = ref('');

    const typeA = ref({
        count: 0,
        question: [
            ["당장 다음주가 제주도 여행인데 단톡방이 조용하다","불안한데... 계획 짜자고 얘기해볼까?","일단 가서 적당히 하면 되겠지~"],
            ["MT를 20명이서 가게 되었는데 나에게 물이 얼마나 필요할지 묻는다", "인당 3L정도 해서 60L정도?", "두세묶음사고 모자르면 편의점 가자~"],
            ["다음 일정이 갑자기 취소되었다", "가려고 했던 곳이 있었는데 그럼 거기로 갈까?", "적당히 아무데나 상관없다"],
        ]
    })
    const typeB = ref({
        count: 0,
        question: [
            ["같이 여행을 간 친구가 내일 호텔 조식을 먹자고 한다", "맛있겠다!! 꼭 가야지~~", "나는 좀..."],
            ["여행 첫날 저녁 8시. 살짝 피곤함이 느껴진다", "내일을 위해서 일찍 들어가 쉴까?", "어림도 없지 바로 카페인 도핑"],
            ["여행지에 도착. 친구가 짜온 계획표를 보니 일정이 빡빡하다", "부지런히 돌아다녀야겠군!", "이거는 굳이 안가도 되지 않을까..?"],
        ]
    })
    const typeC = ref({
        count: 0,
        question: [
            ["제주도에 갔는데 친구가 자전거를 타고 올레길을 돌자고 한다", "너무좋다~~ 바닷가 쪽으로!!", "미안."],
            ["둘중에 한곳을 가야한다. 어느곳으로 갈까?", "실내 암벽장", "악세서리 샵"],
            ["여행지에서 하루를 마무리 하는 방식은?", "마셔마셔~~", "분위기 좋은 곳에서 힐링"],
        ]
    })

    const resultSet = [
        {
            no: 111,
            type: '아메리카노',
            tag: '#계획적인 #아침형 #액티비티',
            explain: '미리 준비된 원두를 사용하고, 아침에 가장 잘 어울리며, 에너지를 충전하는데 효과적인 아메리카노 같은 당신!!',
            src: "../../assets/images/111.png",
            good: '토스트',
            bad: '맥주',
        },
        {
            no: 112,
            type: '토스트',
            tag: '#계획적인 #아침형 #관광',
            explain: '미리 준비된 재료를 사용하고, 아침에 가장 잘 어울리며, 조용히 즐기는 음식인 토스트같은 당신!!',
            src: "../../assets/images/112.png",
            good: '아메리카노',
            bad: '피자',
        },
        {
            no: 121,
            type: '스테이크',
            tag: '#계획적인 #저녁형 #액티비티',
            explain: '미리 준비된 재료를 사용하고, 저녁에 가장 잘 어울리며, 에너지를 충전하는데 효과적인 음식인 스테이크같은 당신!!',
            src: "../../assets/images/121.png",
            good: '와인',
            bad: '시리얼',
        },
        {
            no: 122,
            type: '와인',
            tag: '#계획적인 #저녁형 #관광',
            explain: '미리 준비된 병을 열고, 저녁에 가장 잘 어울리며, 조용히 즐기는 음료인 와인같은 당신!!',
            src: "../../assets/images/122.png",
            good: '스테이크',
            bad: '스무디',
        },
        {
            no: 211,
            type: '스무디',
            tag: '#즉흥적인 #아침형 #액티비티',
            explain: '그때그때 원하는 재료를 넣고, 아침에 가장 잘 어울리며, 에너지를 충전하는데 효과적 음료인 스무디같은 당신!!',
            src: "../../assets/images/211.png",
            good: '시리얼',
            bad: '와인',
        },
        {
            no: 212,
            type: '시리얼',
            tag: '#즉흥적인 #아침형 #관광',
            explain: '그때그때 원하는 재료를 넣고, 아침에 가장 잘 어울리며, 조용히 즐기는 음식인 시리얼 같은 당신!!',
            src: "../../assets/images/212.png",
            good: '스무디',
            bad: '스테이크',
        },
        {
            no: 221,
            type: '피자',
            tag: '#즉흥적인 #저녁형 #액티비티',
            explain: '그때그때 원하는 토핑을 넣고, 저녁에 가장 잘 어울리며, 에너지를 충전하는데 효과적인 음식인 피자같은 당신!!',
            src: "../../assets/images/221.png",
            good: '맥주',
            bad: '토스트',
        },
        {
            no: 222,
            type: '맥주',
            tag: '#즉흥적인 #저녁형 #관광',
            explain: '그때그때 원하는 종류를 선택하고, 저녁에 가장 잘 어울리며, 조용히 즐기는 음료인 맥주같은 당신!!',
            src: "../../assets/images/222.png",
            good: '피자',
            bad: '아메리카노',
        },
    ]

    const startTest = () => {
        typeA.value.count = 0;
        typeB.value.count = 0;
        typeC.value.count = 0;
        resultType.value = '';
    }

    const endTest = async () => {
        if (typeA.value.count < 2) resultType.value += '1';
        else resultType.value += '2';
        if (typeB.value.count < 2) resultType.value += '1';
        else resultType.value += '2';
        if (typeC.value.count < 2) resultType.value += '1';
        else resultType.value += '2';

        console.log(resultType.value)
        typeA.value.count = 0;
        typeB.value.count = 0;
        typeC.value.count = 0;
    }

    
    return {typeA, typeB, typeC, startTest, resultType, endTest, resultSet};
});

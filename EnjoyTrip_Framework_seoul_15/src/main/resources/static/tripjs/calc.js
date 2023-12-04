
function kruskal(n, arr) {
  // 부모 노드 찾기
  function getParent(set, x) {
    if (set[x] === x) return x;
    return (set[x] = getParent(set, set[x]));
  }

  // 두 개의 노드를 같은 부모 노드로 병합
  function unionParent(set, a, b) {
    a = getParent(set, a);
    b = getParent(set, b);

    // 더 작은 값으로 부모 노드 할당
    if (a < b) set[b] = a;
    else set[a] = b;
  }

  // 같은 부모 노드를 갖는지 확인
  function findParent(set, a, b) { // from, to
    a = getParent(set, a);
    b = getParent(set, b);
    
    console.log("PARENT : ", a, b)

    if (a === b) return true;
    else return false;
  }

  // 간선의 비용으로 오름차순 정렬
  arr.sort((a, b) => a[2] - b[2]);

  // 사이클 확인을 위한 배열 생성
  // 각 노드가 어느 그래프에 포함되어 있는지 확인하기 위해
  const set = new Array(n);
  for (let i = 1; i <= n; i++) {
    set[i] = i;
  }

  let cost = 0;
  let result = [];
  for (let i = 0; i < arr.length ; i++) {
    // 동일한 부모를 가르키지 않는 경우에만 선택 -> 사이클이 발생하지 않는 경우
	//console.log(arr[i][0], arr[i][1])
    if (!findParent(set, arr[i][0], arr[i][1])) {
      cost += arr[i][2]; // 비용 추가
      unionParent(set, arr[i][0], arr[i][1]); // 노드 연결
      result.push(arr[i][0]);
      result.push(arr[i][1]);
    }
  }

  return result;
}


// console.log('Hi');

(async ()=> {
    const url='http://localhost:8080/restaurants';
    const response=await fetch(url);
    // const data=await response.json();
    const restaurants=await response.json();

    // console.log(data);
    const element=document.getElementById('app');
    element.innerHTML=`
        ${restaurants.map(restaurant=>`
            <p>
                ${restaurants.id}
                ${restaurants.name}
                ${restaurants.address}
            </p>
        `).join('')}
    `;
        // JSON.stringify(restaurants);
    // <div id="app">
})();
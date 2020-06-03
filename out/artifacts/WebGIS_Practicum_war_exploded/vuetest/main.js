let book = new Vue({
    el:"#book",
    data:{
        books:[
            {
                id: 1,
                name: '《算法导论》',
                date: '2006-9',
                price: 85.00,
                count: 1
            },
            {
                id: 2,
                name: '《UNIX编程艺术》',
                date: '2006-2',
                price: 59.00,
                count: 1
            },
            {
                id: 3,
                name: '《编程珠玑》',
                date: '2008-10',
                price: 39.00,
                count: 1
            },
            {
                id: 4,
                name: '《代码大全》',
                date: '2006-3',
                price: 128.00,
                count: 1
            },
        ]
    },
    computed:{
        totalPrice(){
            // let price = 0;
            // for(let i=0;i<this.books.length;i++){
            //     price += this.books[i].price*this.books[i].count;
            // }
            // return price;
            //
            // let price = 0;
            // for(let book of this.books){
            //     price += book.price*book.count
            // }
            // return price;
            //reduce
            return this.books.reduce(function (pre,now) {
                return pre+now.price;
            },0);
        },
        // reduce

    },
    methods:{
        getFinalPrice(price){
            return "￥"+price.toFixed(2);
        },
        addCount(index){
            this.books[index].count++;
        },
        decreaseCount(index){
            if(this.books[index].count>0)
            this.books[index].count--;
        },
        removeBook(index){
            this.books.splice(index,1)
        },
        booksIsNone(){
            if(this.books.length>0){
                return false;
            }
            else {
                return true;
            }
        }
    },
    filters:{
        showPrice(price){
            return "￥"+price.toFixed(2);
        }
    }
})

let yu = [
    {
        id: 1,
        name: '《算法导论》',
        date: '2006-9',
        price: 85.00,
        count: 1
    },
    {
        id: 2,
        name: '《UNIX编程艺术》',
        date: '2006-2',
        price: 59.00,
        count: 1
    },
    {
        id: 3,
        name: '《编程珠玑》',
        date: '2008-10',
        price: 39.00,
        count: 1
    },
    {
        id: 4,
        name: '《代码大全》',
        date: '2006-3',
        price: 128.00,
        count: 1
    },
];
let a=yu.reduce(function (pre,n) {
    console.log(pre,"++++++++",n);
    return n.price+pre;
},0)
console.log(a);
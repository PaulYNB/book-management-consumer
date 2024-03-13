new Vue({
    el: '#app',  
    data: {
    	contextPath: '/book-management-consumer',
        bookName: '', 
        bookId: '',
        author: '',
        bookIdForGet: '',
        books: [],
        isEditing: false,  
        editingBook: {}
    },  
    methods: {  
        async addBook() {
        	if (this.bookId.length == 0) {
        		alert("Please input book id.");
        		document.getElementById('inputBookId').focus();
        		return;
        	}
        	if (this.bookName.length == 0) {
        		alert("Please input book name.");
        		document.getElementById('inputBookName').focus();
        		return;
        	}
        	if (this.author.length == 0) {
        		alert("Please input author.");
        		document.getElementById('inputAuthor').focus();
        		return;
        	}
        	// 创建一个book对象  
        	const book = {
        	  "id": this.bookId,  
        	  "bookName": this.bookName,  
        	  "author": this.author
        	};
        	const config = {
        			  method: 'POST',  
        			  url: this.contextPath + '/book',  
        			  headers: {   
        			    'Content-Type': 'application/json'  
        			  },  
        			  data: book // 将book对象作为请求体发送  
        			}; 
            const response = await axios(config).then(response => {  
                        alert(response.data);
                        this.bookId = '';
                        this.bookName = '';
                        this.author = '';
                  });
            this.getAllBooks();
        },  
        async deleteBook(id) { 
        	await axios.delete(this.contextPath + '/book/' + id).then(response => {
        		alert(response.data);
        		this.books = this.books.filter(book => book.id !== id);
        	});
             
        },
        async getBooks() {
        	if (this.bookIdForGet.length == 0) {
        		alert("Please input book id.");
        		document.getElementById('inputBookIdForGet').focus();
        		return;
        	}
            await axios.get(this.contextPath + '/book/' + this.bookIdForGet).then(response => {  
                this.books = response.data;
            });          	
        },
        async getAllBooks() {
            axios.get(this.contextPath + '/book').then(response => {  
            	this.books = response.data;
            });
        },
        editBook(index) {  
            this.isEditing = true;  
            this.editingBook = Object.assign({}, this.books[index]);  
        },  
        async saveEdit() {
            const index = this.books.findIndex(book => book.id === this.editingBook.id);  
            if (index !== -1) {
            	
            	// 创建一个book对象  
            	const book = {
            	  "id": this.editingBook.id,  
            	  "bookName": this.editingBook.bookName,  
            	  "author": this.editingBook.author
            	};
            	const config = {
            			  method: 'PUT',  
            			  url: this.contextPath + '/book/' + this.editingBook.bookId,  
            			  headers: {   
            			    'Content-Type': 'application/json'  
            			  },  
            			  data: book // 将book对象作为请求体发送  
            			}; 
                const response = await axios(config).then(response => {  
                        alert(response.data);
                        this.$set(this.books, index, this.editingBook);
                      });            	
            	
//                this.$set(this.books, index, this.editingBook);  
            }  
            this.isEditing = false;  
        },  
        cancelEdit() {  
            this.isEditing = false;  
        },
    },
    created() {
    	this.getAllBooks();
    }  
});
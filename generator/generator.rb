require 'jsonclient'
require 'ostruct'
require 'json'
require 'csv'

HOST = 'http://posttestserver.com/post.php?dir=sn' # 'http://localhost:8080'
USERS_CSV = 'users.csv'

users_rows = CSV.read(USERS_CSV)
users_attributes = users_rows.shift

users_rows.each.with_index do |row, id|
  columns_count = users_attributes.size
  attributes = users_attributes[1..-1].zip(row[1..-1]).to_h.merge(tags: row[(columns_count - 1)..-1])
  define_method(row[0]) {
    OpenStruct.new({ id: id }.merge(attributes))
  }
end

articles = [
  OpenStruct.new(
    title: 'Java wygrywa!',
    description: 'Mądry artykuł',
    tags: ['java']
  ),
  OpenStruct.new(
    title: 'Kompresjoterapia jako pomoc w leczeniu żylaków',
    description: 'Mądrości',
    tags: ['compression therapy']
  ),
]

def profile_info(user)
  {
    type: 'PROFILE_INFO',
    timestamp: Time.now.to_s,
    payload: {
      userId: user.id.to_s,
      firstName: user.first_name,
      lastName: user.last_name,
      gender: user.gender,
      city: user.city,
      job: user.job,
      university: user.university,
      tags: user.tags
    }
  }
end

def like(user, article)
  {
    type: 'LIKE',
    timestamp: Time.now.to_s,
    payload: {
      userId: user.id.to_s,
      title: article.title,
      description: article.description,
      hashTags: article.tags
    }
  }
end

def send(hash)
  JSONClient.new.post(HOST, body: hash)
  puts hash.to_json
end

[
  profile_info(arek),
  profile_info(basia),
  like(arek, articles.first),
  profile_info(ewa),
  profile_info(daniel),
  profile_info(felicja),
  like(felicja, articles.last),
].each { |hash| send(hash) }

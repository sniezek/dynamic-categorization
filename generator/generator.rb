require 'jsonclient'
require 'ostruct'
require 'kafka'
require 'json'
require 'csv'

HOST = 'http://posttestserver.com/post.php?dir=sn' # 'http://localhost:8080'
USERS_CSV = 'users.csv'
KAFKA_CLIENT = Kafka.new(seed_brokers: ['localhost:9092'])

users_rows = CSV.read(USERS_CSV, encoding: 'utf-8')
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
    title: 'JVM wygrywa!',
    description: 'Madry artykul',
    tags: %w[java scala]
  ),
  OpenStruct.new(
    title: 'Kompresjoterapia jako pomoc w leczeniu zylakow',
    description: 'Madrosci',
    tags: ['compressiontherapy']
  ),
  OpenStruct.new(
    title: 'Czy jezyki obiektowe sa lepsze?',
    tags: %w[Erlang Java]
  ),
]

def profile_info(user, attributes_to_merge = {})
  user = OpenStruct.new(user.to_h.merge(attributes_to_merge))
  {
    userId: user.id.to_s,
    date: Time.now.strftime('%Y-%m-%dT%H:%M:%S.%3NZ'),
    payload: {
      type: 'PROFILE_INFO',
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
    
    userId: user.id.to_s,
    date: Time.now.strftime('%Y-%m-%dT%H:%M:%S.%3NZ'),
    payload: {
      type: 'LIKE',
      title: article.title,
      description: article.description,
      hashTags: article.tags
    }
  }
end

def send(hash)
  KAFKA_CLIENT.deliver_message(hash.to_json, topic: 'fb')
  # JSONClient.new.post(HOST, body: hash)
  hash
end

puts [
  profile_info(arek),
  profile_info(basia),
  like(arek, articles[0]),
  like(arek, articles[1]),
  profile_info(ewa),
  profile_info(daniel),
  profile_info(felicja),
  like(felicja, articles[1]),
  profile_info(grzegorz),
  profile_info(helena),
  like(helena, articles[2]),
  profile_info(igor),
  like(igor, articles[2]),
  profile_info(jennifer),
  like(jennifer, articles[2]),
  profile_info(helena, job: 'computer interfaces designer', tags: helena.tags + ['java']),
  profile_info(daniel, city: 'Cracow')
].map { |hash| send(hash) }.to_json
